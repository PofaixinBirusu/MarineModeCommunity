package xurong.marinemode.community.dto;

import xurong.marinemode.community.model.User;
import xurong.marinemode.community.utils.PlatformKey;

public class GitHubUser extends User {
    private String name;
    GitHubUser() {
        this.platform = PlatformKey.GitHub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Integer.parseInt(accountId);
    }

    public void setId(String id) {
        this.accountId = id;
    }

    public String getBio() {
        return biography;
    }

    public void setBio(String bio) {
        this.biography = bio;
    }

    public String getLogin() {
        return username;
    }

    public void setLogin(String login) {
        this.username = login;
    }

    public String getAvatar_url() {
        return profilePicture;
    }

    public void setAvatar_url(String avatar_url) {
        this.profilePicture = avatar_url;
    }
}
