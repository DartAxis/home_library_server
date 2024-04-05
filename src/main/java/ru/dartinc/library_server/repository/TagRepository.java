package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag getTagByTagTitleIgnoreCase(String tag);
}
