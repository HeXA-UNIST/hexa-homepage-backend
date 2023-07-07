package pro.hexa.backend.domain.project_member.domain;

import lombok.Getter;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.model.model.AbstractEntity;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project_member.model.AUTHORIZATION_TYPE;
import pro.hexa.backend.domain.user.domain.User;

import javax.persistence.*;

@Entity(name = "project_member")
@Getter
public class ProjectMember extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private Long id; // 왜 string이노

    @Comment("프로젝트")
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Comment("참여 맴버")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Comment("권한")
    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private AUTHORIZATION_TYPE authorization;
}
