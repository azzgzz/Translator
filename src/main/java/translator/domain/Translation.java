package translator.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="translations")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDate tnDate;

    @Column
    private LocalTime tnTime;

    @Column(length = 1000)
    private String tnText;

    @Column
    private String tnFrom;

    @Column
    private String tnTo;

    @Column
    private String tnResult;

    public Translation() {
    }

    public Translation(Long id, LocalDate tnDate, LocalTime tnTime, String tnText, String tnFrom, String tnTo, String tnResult) {
        this.id = id;
        this.tnDate = tnDate;
        this.tnTime = tnTime;
        this.tnText = tnText;
        this.tnFrom = tnFrom;
        this.tnTo = tnTo;
        this.tnResult = tnResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTnDate() {
        return tnDate;
    }

    public void setTnDate(LocalDate tnDate) {
        this.tnDate = tnDate;
    }

    public LocalTime getTnTime() {
        return tnTime;
    }

    public void setTnTime(LocalTime tnTime) {
        this.tnTime = tnTime;
    }

    public String getTnText() {
        return tnText;
    }

    public void setTnText(String tnText) {
        this.tnText = tnText;
    }

    public String getTnFrom() {
        return tnFrom;
    }

    public void setTnFrom(String tnFrom) {
        this.tnFrom = tnFrom;
    }

    public String getTnTo() {
        return tnTo;
    }

    public void setTnTo(String tnTo) {
        this.tnTo = tnTo;
    }

    public String getTnResult() {
        return tnResult;
    }

    public void setTnResult(String tnResult) {
        this.tnResult = tnResult;
    }
}
