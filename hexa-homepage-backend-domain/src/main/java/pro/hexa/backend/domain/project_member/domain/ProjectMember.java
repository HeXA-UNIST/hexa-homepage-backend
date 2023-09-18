package pro.hexa.backend.domain.project_member.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.model.model.AbstractEntity;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.model.STATE_TYPE;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.project_member.model.AUTHORIZATION_TYPE;

@Entity(name = "project_member")
@Getter
public class ProjectMember extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private String id;

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

    public static ProjectMember create(
            String id,
            User user,
            AUTHORIZATION_TYPE authorization

    ) {
        ProjectMember projectmember = new ProjectMember();
        projectmember.id = id;
        projectmember.user = user;
        projectmember.authorization = authorization;
        return projectmember;
    }
}
