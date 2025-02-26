package com.MIE350.FitnessRoutineHub.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    private String description;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "friendships",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();

    public Set<Long> getFriends() {
        return this.friends
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

    public Set<User> getFriendsUser() {
        return this.friends;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
        friend.friends.add(this);
    }

    public void removeFriend(User friend) {
        this.friends.remove(friend);
        friend.friends.remove(this);
    }

    @OneToOne
    private FitnessCalendar fitnessCalendar;

    @NotNull
    private Instant createdAt;

    private Instant updateAt;

}
