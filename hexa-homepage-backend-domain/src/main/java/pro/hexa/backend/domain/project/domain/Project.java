package pro.hexa.backend.domain.project.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.model.model.AbstractEntity;
import pro.hexa.backend.domain.project.model.STATE_TYPE;
import pro.hexa.backend.domain.project_member.domain.ProjectMember;
import pro.hexa.backend.domain.project_member.model.AUTHORIZATION_TYPE;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;

@Entity(name = "project")
@Getter
public class Project extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Comment(value = "제목")
    @Column(length = 300)
    private String title;

    @Comment(value = "시작일")
    @Column
    private LocalDateTime startDate;

    @Comment(value = "종료일")
    @Column
    private LocalDateTime endDate;

    @Comment(value = "기술스택")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProjectTechStack> projectTechStacks = new ArrayList<>();

    @Comment(value = "멤버")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProjectMember> members = new ArrayList<>();

    @Comment(value = "노출")
    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private AUTHORIZATION_TYPE authorization;

    @Comment(value = "상태")
    @Enumerated(EnumType.STRING)
    @Column(length = 4)
    private STATE_TYPE state;

    @Comment(value = "내용")
    @Column(length = 3000)
    private String content;

    @Comment("썸네일")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Attachment thumbnail;

    public static Project create(
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<ProjectTechStack> projectTechStacks,
        List<ProjectMember> members,
        AUTHORIZATION_TYPE authorization,
        STATE_TYPE state,
        String content,
        Attachment thumbnail

    ) {
        Project project = new Project();
        project.title = title;
        project.startDate = startDate;
        project.endDate = endDate;
        project.projectTechStacks = projectTechStacks;
        project.members = members;
        project.authorization = authorization;
        project.state = state;
        project.content = content;
        project.thumbnail = thumbnail;
        return project;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setProjectTechStacks(List<ProjectTechStack> projectTechStacks) {
        this.projectTechStacks = projectTechStacks;
    }

    public void setState(STATE_TYPE state) {
        this.state = state;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setThumbnail(Attachment thumbnail) {
        this.thumbnail = thumbnail;
    }

}
