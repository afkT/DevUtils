package afkt.project.database.room.bean;

import java.util.Date;
import java.util.List;

import afkt.project.database.green.bean.NotePicture;
import afkt.project.database.green.bean.NoteType;

public class Note {

    private Long   id;
    private String text;
    private String comment;
    private Date   date;

    private NoteType          type;
    private List<NotePicture> pictures;
}