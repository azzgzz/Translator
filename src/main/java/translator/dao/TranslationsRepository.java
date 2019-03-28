package translator.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import translator.domain.Translation;


@Repository
public interface TranslationsRepository extends JpaRepository<Translation, Long> {}
