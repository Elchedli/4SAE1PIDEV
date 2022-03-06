package com.voyageAffaires.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reclamation")
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_rec", nullable = false)
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String message;

    private String fileName;

    private String fileType;

    @NotNull(message = "Date must be not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReclamation;

    @Lob
    private byte[] data;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "reclamation",cascade = CascadeType.ALL)
    private List<ReponseReclamation>  reponseReclamations;




}
