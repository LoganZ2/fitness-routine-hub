package com.MIE350.FitnessRoutineHub.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<Post> posts;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "friendships",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();

    public Set<Long> getFriendsLong() {
        return this.friends
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
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
    @JsonManagedReference
    private FitnessCalendar fitnessCalendar;

    @OneToOne
    @JsonManagedReference
    private HealthProfile healthProfile;

    @NotNull
    private Instant createdAt;

    private Instant updateAt;

}
