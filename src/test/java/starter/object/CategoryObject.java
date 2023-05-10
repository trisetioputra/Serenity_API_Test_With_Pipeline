package starter.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryObject {
    Integer ID;
    String Name;
    String Description;

    public Integer getID() {
        return ID;
    }
    @JsonProperty("ID")
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }
    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }
    @JsonProperty("Description")
    public void setDescription(String description) {
        Description = description;
    }
}
