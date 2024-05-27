package pro.hexa.backend.domain.project_tech_stack.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import pro.hexa.backend.domain.model.model.AbstractEntity;

@Entity(name = "project_tech_stack")
@Getter
public class ProjectTechStack extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_tech_stack_id")
    private Long id;

    @Comment("내용")
    @Column(length = 127)
    private String content;


    public static ProjectTechStack create(
        String content
    ) {
        ProjectTechStack projectTechStack = new ProjectTechStack();
        projectTechStack.content = content;
        return projectTechStack;
    }
}

