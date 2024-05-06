package iiproject.jobhunting.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="follow_company_db")
public class FollowCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="follow_company_id")
    private int roleId;

    @Column(name="user_id")
    private int userId;

    @Column(name="company_id")
    private int companyId;
}
