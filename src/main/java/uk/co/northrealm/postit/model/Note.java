package uk.co.northrealm.postit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.google.common.base.MoreObjects;

@Entity
@Table(indexes = @Index(columnList = "sequence"))
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(nullable = false, unique = true)
    private long sequence;
    
	@Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date timestamp;
    
	@NotBlank(message = "Title is mandatory")
    @Size(max = 50, message = "Title cannot be longer than 50 characters")
    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 250)
    @Size(max = 250, message = "Content cannot be longer than 250 characters")
    private String content;
    
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

	public Note() {
	}

    public Note(Date timestamp, String title, String content) {
    	this.title = title;
    	this.content = content;
        this.timestamp = timestamp;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }
  
    public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

    public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
    public String toString() {
		return MoreObjects.toStringHelper(this)
	     .add("id", id)
	     .add("sequence", sequence)
	     .add("timestamp", timestamp)
	     .add("title", title)
	     .add("content", content)
	     .add("status", status)
	     .toString();
    }
}
