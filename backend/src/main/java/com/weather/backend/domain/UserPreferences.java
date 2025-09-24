package com.weather.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_preferences")
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean showWeatherSearch = true;

    @Column(nullable = false)
    private boolean showHourlyForecast = true;

    @Column(nullable = false)
    private boolean show7DayForecast = true;

    @Column(nullable = false)
    private boolean showStats = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isShowWeatherSearch() {
        return showWeatherSearch;
    }

    public void setShowWeatherSearch(boolean showWeatherSearch) {
        this.showWeatherSearch = showWeatherSearch;
    }

    public boolean isShowHourlyForecast() {
        return showHourlyForecast;
    }

    public void setShowHourlyForecast(boolean showHourlyForecast) {
        this.showHourlyForecast = showHourlyForecast;
    }

    public boolean isShow7DayForecast() {
        return show7DayForecast;
    }

    public void setShow7DayForecast(boolean show7DayForecast) {
        this.show7DayForecast = show7DayForecast;
    }

    public boolean isShowStats() {
        return showStats;
    }

    public void setShowStats(boolean showStats) {
        this.showStats = showStats;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
