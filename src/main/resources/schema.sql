DROP TABLE IF EXISTS films_genres;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS review_grades;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS friend_statuses;
DROP TABLE IF EXISTS mpa;

CREATE TABLE IF NOT EXISTS films (
    film_id  SERIAL  NOT NULL,
    name varchar(100)   NOT NULL,
    description varchar(200)   NOT NULL,
    release_date date   NOT NULL,
    duration int   NOT NULL,
    mpa_mpa_id int   NOT NULL,
    CONSTRAINT pk_films PRIMARY KEY (
        film_id
     )
);

CREATE TABLE IF NOT EXISTS users (
    user_id  SERIAL  NOT NULL,
    email varchar(100)   NOT NULL,
    login varchar(100)   NOT NULL,
    name varchar(100)   NOT NULL,
    birthday date   NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (
        user_id
     )
);

CREATE TABLE IF NOT EXISTS genres (
    genre_id  SERIAL  NOT NULL,
    name varchar(100)   NOT NULL,
    CONSTRAINT pk_genres PRIMARY KEY (
        genre_id
     )
);

CREATE TABLE IF NOT EXISTS films_genres (
    film_film_id int  NOT NULL,
    genre_genre_id int  NOT NULL
);

CREATE TABLE IF NOT EXISTS mpa (
    mpa_id  SERIAL  NOT NULL,
    name varchar(50)   NOT NULL,
    CONSTRAINT pk_mpa PRIMARY KEY (
        mpa_id
     )
);

CREATE TABLE IF NOT EXISTS friends (
    first_user_id int   NOT NULL,
    second_user_id int   NOT NULL,
    frstat_frstat_id int   NOT NULL
);

CREATE TABLE IF NOT EXISTS friend_statuses (
    frstat_id int   NOT NULL,
    name varchar(100)   NOT NULL,
    CONSTRAINT pk_friend_statuses PRIMARY KEY (
        frstat_id
     )
);

CREATE TABLE IF NOT EXISTS likes (
    film_film_id int   NOT NULL,
    user_user_id int   NOT NULL
);

CREATE TABLE reviews (
    rev_id  identity  NOT NULL,
    film_film_id int   NOT NULL,
    user_user_id int   NOT NULL,
    content text   NOT NULL,
    is_positive boolean   NOT NULL,
    CONSTRAINT pk_reviews PRIMARY KEY (
        rev_id
     )
);

CREATE TABLE review_grades (
    rev_rev_id int   NOT NULL,
    user_user_id int   NOT NULL,
    is_positive boolean
);

ALTER TABLE films ADD CONSTRAINT IF NOT EXISTS fk_films_mpa_mpa_id FOREIGN KEY(mpa_mpa_id)
REFERENCES mpa (mpa_id) ON DELETE CASCADE;

ALTER TABLE films_genres ADD CONSTRAINT IF NOT EXISTS fk_films_genres_film_film_id FOREIGN KEY(film_film_id)
REFERENCES films (film_id) ON DELETE CASCADE;

ALTER TABLE films_genres ADD CONSTRAINT IF NOT EXISTS fk_films_genres_genre_genre_id FOREIGN KEY(genre_genre_id)
REFERENCES genres (genre_id) ON DELETE CASCADE;

ALTER TABLE friends ADD CONSTRAINT IF NOT EXISTS fk_friends_first_user_id FOREIGN KEY(first_user_id)
REFERENCES users (user_id) ON DELETE CASCADE;

ALTER TABLE friends ADD CONSTRAINT IF NOT EXISTS fk_friends_second_user_id FOREIGN KEY(second_user_id)
REFERENCES users (user_id) ON DELETE CASCADE;

ALTER TABLE friends ADD CONSTRAINT IF NOT EXISTS fk_friends_frstat_frstat_id FOREIGN KEY(frstat_frstat_id)
REFERENCES friend_statuses (frstat_id) ON DELETE CASCADE;

ALTER TABLE likes ADD CONSTRAINT IF NOT EXISTS fk_likes_film_film_id FOREIGN KEY(film_film_id)
REFERENCES films (film_id) ON DELETE CASCADE;

ALTER TABLE likes ADD CONSTRAINT IF NOT EXISTS fk_likes_user_user_id FOREIGN KEY(user_user_id)
REFERENCES users (user_id) ON DELETE CASCADE;

ALTER TABLE reviews ADD CONSTRAINT fk_reviews_film_film_id FOREIGN KEY(film_film_id)
REFERENCES films (film_id) ON DELETE CASCADE;

ALTER TABLE reviews ADD CONSTRAINT fk_reviews_user_user_id FOREIGN KEY(user_user_id)
REFERENCES users (user_id) ON DELETE CASCADE;

ALTER TABLE review_grades ADD CONSTRAINT fk_review_grades_rev_rev_id FOREIGN KEY(rev_rev_id)
REFERENCES reviews (rev_id) ON DELETE CASCADE;

ALTER TABLE review_grades ADD CONSTRAINT fk_review_grades_user_user_id FOREIGN KEY(user_user_id)
REFERENCES users (user_id) ON DELETE CASCADE;
