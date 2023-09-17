package bctc.back.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Parent extends User{
    @NotNull
    @Email(regexp = ".+@.+\\..+")
    public String email;

    // https://regex101.com/r/eE5hI4/1
    @Pattern(regexp = "/(^8|7|\\+7)((\\d{10})|(\\s\\(\\d{3}\\)\\s\\d{3}\\s\\d{2}\\s\\d{2}))/")
    @NotNull
    public String phone;

    @ManyToMany(mappedBy = "parents")
    public List<Student> students;
}
