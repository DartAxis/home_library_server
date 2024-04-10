package ru.dartinc.library_server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.dartinc.library_server.dto.BookInDTO;
import ru.dartinc.library_server.model.*;
import ru.dartinc.library_server.repository.BookRepository;
import ru.dartinc.library_server.security.model.User;
import ru.dartinc.library_server.utils.Base64Utils;
import ru.dartinc.library_server.utils.FileLibUtils;
import ru.dartinc.library_server.utils.SevenZCompress;
import ru.dartinc.library_server.utils.SevenZDecompress;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    private final AuthorService authorService;
    private final GenreService genreService;
    private final PubHouseService pubHouseService;
    private final TagService tagService;
    private final SeriaService seriaService;

    private final HistoryViewService historyViewService;

    @Value("${pathtostorage}")
    private String pathToStorage;

    @Value("${pathtotempstorage}")
    private String pathToTempStorage;

    @Value("${pathtotemp}")
    private String pathToTemp;

    @Value("${pathtopictures}")
    private String pathToPictures;


    public List<Book> getAllBooksToFront() {
        var books = repository.findAll();
        return books;
    }

    public Book getBookById(Long id) {
        var book = repository.findById(id);
        return book.orElse(null);
    }

    public String getBookInfoById(Long id) {
        var book = getBookById(id);
        if (book != null) {
            return bookToStringInfo(book);
        }
        return "Не найдена книга с id =" + id;
    }

    public String bookToStringInfo(Book book) {
        StringBuilder builder = new StringBuilder();
        builder.append("UUID: ").append(book.getUuid()).append("\n");
        builder.append("Название: ").append(book.getTitle()).append("\n");
        if(book.getSeria()!=null) {
            builder.append("Название серии: ").append(book.getSeria().getSeriaTitle()).append("\n");
        }
        builder.append("Оригинальное название: ").append(book.getOriginalTitle()).append("\n");
        builder.append("Год издания оригинала: ").append(book.getYearOfEdition()).append("\n");
        if(book.getYearOfEditionTranslate()!=null) {
            builder.append("Год издания перевода: ").append(book.getYearOfEditionTranslate()).append("\n");
        }
        builder.append("ISBN оригинала: ").append(book.getIsbnOriginal()).append("\n");
        if(book.getIsbnTranslate()!=null) {
            builder.append("ISBN перевода: ").append(book.getIsbnTranslate()).append("\n");
        }
        builder.append("Формат книги: ").append(book.getFileFormatBook()).append("\n");
        builder.append("Имя файла архива: ").append(book.getPathToZipBook()).append("\n");
        builder.append("Жанр: ").append(book.getGenre().getGenreTitle()).append("\n");
        builder.append("Издательство оригинала: ").append(book.getPubHouse().getPubHouseName()).append("\n");
        if(book.getPubHouseTranslate()!=null) {
            builder.append("Издательство перевода: ").append(book.getPubHouseTranslate().getPubHouseName()).append("\n");
        }
        builder.append("Авторы: ").append(setStringToString(book.getAuthors()
                .stream()
                .map(a -> a.getSurname() + (a.getName() != null ? " " + a.getName() : "") + (a.getMiddlename() != null ? " " + a.getMiddlename() : "")).collect(Collectors.toSet()))).append("\n");
        builder.append("Теги: ").append(setStringToString(book.getTags()
                        .stream()
                        .map(Tag::getTagTitle)
                        .collect(Collectors.toSet())))
                .append("\n");
        builder.append("\n\n ");
        return builder.toString();
    }

    public String setStringToString(Set<String> stringSet) {
        StringBuilder sb = new StringBuilder();
        stringSet.forEach(s -> sb.append(s).append("; "));
        return sb.toString();
    }

    public String addBook(BookInDTO bookInDTO) {
        var book = new Book();
        if (bookInDTO.getTitle() != null && !bookInDTO.getTitle().isEmpty() && !bookInDTO.getTitle().isBlank()) {
            if (checkBookTitle(bookInDTO.getTitle())) {
                book.setTitle(bookInDTO.getTitle());
            }
        } else {
            return "Пустое название книги";
        }

        if (bookInDTO.getOriginalTitle() != null && !bookInDTO.getOriginalTitle().isEmpty() && !bookInDTO.getOriginalTitle().isBlank()) {
            if (checkBookOriginalTitle(bookInDTO.getOriginalTitle())) {
                book.setOriginalTitle(bookInDTO.getOriginalTitle());
            }
        } else {
            return "Пустое имя оригинала книги";
        }

        book.setAuthors(processAuthors(bookInDTO.getAuthors()));
        book.setTags(processTags(bookInDTO.getTags()));
        book.setGenre(checkOrAddGenre(bookInDTO.getGenre()));
        if (bookInDTO.getSeria() != null && !bookInDTO.getSeria().isEmpty() && !bookInDTO.getSeria().isBlank()) {
            book.setSeria(checkOrAddSeria(bookInDTO.getSeria()));
        }

        if (bookInDTO.getPubHouse() != null && !bookInDTO.getPubHouse().isEmpty() && !bookInDTO.getPubHouse().isBlank()) {
            book.setPubHouse(checkOrAddPubHouse(bookInDTO.getPubHouse()));
            book.setYearOfEdition(bookInDTO.getYearEdition());
            if (bookInDTO.getIsbnOriginal()!=null && checkIsbnOriginal(bookInDTO.getIsbnOriginal())) {
                book.setIsbnOriginal(bookInDTO.getIsbnOriginal());
            }
        }
        if (bookInDTO.getPubHouseTranslate() != null && !bookInDTO.getPubHouseTranslate().isEmpty() && !bookInDTO.getPubHouseTranslate().isBlank()) {
            book.setPubHouseTranslate(checkOrAddPubHouse(bookInDTO.getPubHouseTranslate()));
            book.setYearOfEditionTranslate(bookInDTO.getYearOfEditionTranslate());
            if (bookInDTO.getIsbnTranslate()!=null && checkIsbnTranslate(bookInDTO.getIsbnTranslate())) {
                book.setIsbnTranslate(bookInDTO.getIsbnTranslate());
            }
        }

        if (bookInDTO.getFileFormatBook() != null && !bookInDTO.getFileFormatBook().isEmpty() && !bookInDTO.getFileFormatBook().isBlank()) {
            book.setFileFormatBook(bookInDTO.getFileFormatBook().toUpperCase());
        } else {
            return "Не указан формат книги";
        }
        book.setUuid(UUID.randomUUID());
        if(bookInDTO.getFile()!=null && !bookInDTO.getFile().isEmpty() && !bookInDTO.getFile().isBlank()){
            book.setPathToZipBook(book.getUuid() + ".7z");
            var bookfile = book.getUuid() + "." + book.getFileFormatBook().toLowerCase();
            var bookPictureFile = book.getUuid() + ".png";
            var pathToTempBookFile = pathToTemp + bookfile;
            var pathToBookTextInfoFile = pathToTemp+"info";
            var pathToTempBookPicture = pathToTemp + bookPictureFile;
            var pathToTemp7z = pathToTempStorage + book.getPathToZipBook();
            var pathToStorage7z = pathToStorage + book.getPathToZipBook();
            if(!Base64Utils.base64ToFile(bookInDTO.getFile(), pathToTempBookFile)) {
                return "Не создался файл переданной книги";
            }
            if(bookInDTO.getPicture() != null && !bookInDTO.getPicture().isEmpty() && !bookInDTO.getPicture().isBlank()){
                Base64Utils.base64ToFile(bookInDTO.getPicture(),pathToTempBookPicture);
                book.setPathToPic(bookPictureFile);
                SevenZCompress.copyFile7zOutBookTemp(new File(pathToTempBookPicture),new File(pathToPictures+bookPictureFile));
            } else {
                book.setPathToPic("noimage.png");
            }
            //Получить текстовку инфы о книге и сохранить в текстовый файл в темповую дирректорию
            var createStringBookInfoFile = FileLibUtils.stringToFile(pathToBookTextInfoFile,bookToStringInfo(book));


            //запаковать в 7z
            if(createStringBookInfoFile && Files.exists(Paths.get(pathToBookTextInfoFile)) && Files.exists(Paths.get(pathToTempBookFile))) {
                if(SevenZCompress.compress(pathToTemp7z, new File(pathToBookTextInfoFile), new File(pathToTempBookFile))){
                    // скопировать 7z в целевую папку хранилища
                    SevenZCompress.copyFile7zOutBookTemp(new File(pathToTemp7z),new File(pathToStorage7z));
                    // очистить темповую папку с архивом
                    SevenZCompress.cleanBookTemp(new File(pathToTempStorage));
                    // очистить временную папку
                    SevenZCompress.cleanBookTemp(new File(pathToTemp));
                } else {
                    return "Не удалось заархивировать файлы";
                }
            }
        } else {
            return "В теле запроса нет файла";
        }
        book.setAddDate(LocalDateTime.now());
        repository.save(book);
        return "Книга добавлена :";
    }

    protected boolean checkBookTitle(String title) {
        return repository.getBookByTitleIgnoreCase(title) == null;
    }

    protected boolean checkBookOriginalTitle(String title) {
        return repository.getBookByOriginalTitleIgnoreCase(title) == null;
    }

    protected boolean checkIsbnOriginal(String isbn) {
        return repository.getBookByIsbnOriginal(isbn) == null;
    }

    protected boolean checkIsbnTranslate(String isbn) {
        return repository.getBookByIsbnTranslate(isbn) == null;
    }

    protected PubHouse checkOrAddPubHouse(String pubHouseName) {
        return pubHouseService.add(pubHouseName);
    }

    protected Seria checkOrAddSeria(String seriaTitle) {
        return seriaService.add(seriaTitle);
    }


    //Обработка жанра
    protected Genre checkOrAddGenre(String genreTitle) {
        return genreService.add(genreTitle);
    }

    //Обработка Тагов
    protected Set<Tag> processTags(Set<String> tags) {
        return tags.stream().map(this::checkOrAddTag).collect(Collectors.toSet());
    }

    protected Tag checkOrAddTag(String tagStr) {
        return tagService.add(tagStr);
    }

    //Обработка Авторов
    protected Set<Author> processAuthors(Set<String> authors) {
        return authors.stream().map(this::checkOrAddAuthor).collect(Collectors.toSet());
    }

    protected Author checkOrAddAuthor(String authorStr) {
        String[] arrFio = authorStr.split(" ");
        Author result = null;
        if (arrFio.length == 2) {
            result = authorService.getByFio(arrFio[0], arrFio[1], null);
        }
        if (arrFio.length == 3) {
            result = authorService.getByFio(arrFio[0], arrFio[1], arrFio[2]);
        }

        if (result == null) {
            log.info("Добавляем автора: {}", authorStr);
            if (arrFio.length == 1) {
                result = authorService.add(arrFio[0], null, null);
            }
            if (arrFio.length == 2) {
                result = authorService.add(arrFio[0], arrFio[1], null);
            }
            if (arrFio.length == 3) {
                result = authorService.add(arrFio[0], arrFio[1], arrFio[2]);
            }
        }
        return result;
    }


    public String getPicture(Long id) {
        var result =getBookById(id);
        if(result!= null){
            return pathToPictures + result.getPathToPic();
        }
        return null;
    }

    public String getArchiveFile(Long id) {
        var result =getBookById(id);
        if(result!= null){
            return pathToStorage + result.getPathToZipBook();
        }
        return null;
    }
    public String getBookFile(Long id) {
        var result =getBookById(id);
        User principal =(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(result!= null){
            try {
                SevenZDecompress.decompress(pathToStorage + result.getPathToZipBook(), pathToTemp);
                HistoryView historyElement = new HistoryView();
                historyElement.setBook(result);
                historyElement.setUser(principal);
                historyElement.setViewDate(LocalDateTime.now());
                historyViewService.save(historyElement);
                return pathToTemp + result.getUuid()+"."+result.getFileFormatBook().toLowerCase();
            } catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public boolean clearTempDir(){
        SevenZCompress.cleanBookTemp(new File(pathToTemp));
        return true;
    }
}
