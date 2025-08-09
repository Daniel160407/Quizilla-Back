package com.quizilla.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "game_id")
    private Integer gameId;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "type")
    private String type;
    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;
    @Column(name = "media_url")
    private String mediaUrl;
    @Column(name = "points")
    private Double points;
    @Column(name = "hint")
    private String hint;
    @Column(name = "enabled")
    private Integer enabled;
}
