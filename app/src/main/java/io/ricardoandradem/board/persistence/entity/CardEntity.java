package io.ricardoandradem.board.persistence.entity;

public class CardEntity {

    private Long id;
    private String Title;
    private String Description;
    private BlockStatus isBlocked;
    private String blockDescription;

    public CardEntity() {
    }

    public CardEntity(Long id, String title, String description, BlockStatus isBlocked, String blockDescription) {
        this.id = id;
        Title = title;
        Description = description;
        this.isBlocked = isBlocked;
        this.blockDescription = blockDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public BlockStatus getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(BlockStatus isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getBlockDescription() {
        return blockDescription;
    }

    public void setBlockDescription(String blockDescription) {
        this.blockDescription = blockDescription;
    }

    @Override
    public String toString() {
        return "CardEntity{" +
                "id=" + id +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", isBlocked=" + isBlocked +
                ", blockDescription='" + blockDescription + '\'' +
                '}';
    }
}
