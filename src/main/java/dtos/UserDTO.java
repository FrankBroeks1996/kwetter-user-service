package dtos;

import models.Role;
import models.User;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    private UUID id;

    private String username;

    private String location;

    private String website;

    private String bio;

    private String profilePicturePath;

    private Role role;

    private List<UUID> kweets;

    private List<UUID> mentionedIn;

    public UserDTO(){}

    public UserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.location = user.getLocation();
        this.website = user.getWebsite();
        this.bio = user.getBio();
        this.profilePicturePath = user.getProfilePicturePath();
        this.role = user.getRole();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<UUID> getKweets() {
        return kweets;
    }

    public void setKweets(List<UUID> kweets) {
        this.kweets = kweets;
    }

    public List<UUID> getMentionedIn() {
        return mentionedIn;
    }

    public void setMentionedIn(List<UUID> mentionedIn) {
        this.mentionedIn = mentionedIn;
    }
}
