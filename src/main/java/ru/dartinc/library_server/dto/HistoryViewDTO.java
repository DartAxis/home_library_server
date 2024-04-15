package ru.dartinc.library_server.dto;

import lombok.Getter;
import lombok.Setter;
import ru.dartinc.library_server.model.HistoryDownloadArchive;
import ru.dartinc.library_server.model.HistoryView;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoryViewDTO {
    private BookOutSmallDTO book;
    private LocalDateTime date;

    public HistoryViewDTO(HistoryView historyView){
        this.book = new BookOutSmallDTO(historyView.getBook());
        this.date = historyView.getViewDate();
    }

    public HistoryViewDTO(HistoryDownloadArchive historyView){
        this.book = new BookOutSmallDTO(historyView.getBook());
        this.date = historyView.getDownloadArchiveDate();
    }
}
