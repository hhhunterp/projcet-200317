package com.atguigu.bean;

/**
 * @Author: HunterP
 * @Date: 2020/8/10 11:11
 * @DESC:
 */

public class Movie {

    private String id;
    private String movie_name;

    public Movie() {
    }

    public Movie(String id, String movie_name) {
        this.id = id;
        this.movie_name = movie_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != null ? !id.equals(movie.id) : movie.id != null) return false;
        return movie_name != null ? movie_name.equals(movie.movie_name) : movie.movie_name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (movie_name != null ? movie_name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", movie_name='" + movie_name + '\'' +
                '}';
    }
}
