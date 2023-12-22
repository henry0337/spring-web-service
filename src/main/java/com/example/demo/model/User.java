package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "display_name", nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    @Email(message = "Địa chỉ email không hợp lệ.")
    private String email;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-z0-9]+$", message = "Tên người dùng chỉ có thể chứa chữ viết thường và số.")
    private String username;

    @Column(nullable = false)
    @Length(min = 8, message = "Độ dài mật khẩu tối thiểu là 8 kí tự.")
    private String password;

    @Column(columnDefinition = "smallint default 1")
    @Min(value = 0, message = "Giá trị của trường này phải lớn hơn hoặc bằng 0")
    @Max(value = 1, message = "Giá trị của trường này phải nhỏ hơn hoặc bằng 1")
    private Short status = 1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, columnDefinition = "timestamp default current_timestamp(1)")
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", insertable = false, columnDefinition = "timestamp default current_timestamp(1)")
    private Date updatedAt;
}
