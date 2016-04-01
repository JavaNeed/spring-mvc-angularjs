Spring 4 MVC+Hibernate Many-to-many JSP Example with annotation
------------------------------------------

App URL:
-------
http://localhost:8080/SpringMVCHibernateManyToManyCRUDExample/


This post demonstrates Hibernate Many-to-many example, with join table in Spring MVC CRUD Web application. We will discuss managing Many-to-Many relationship both in views and back-end. We will perform Create, Update, Delete & Query all using application Web interface.

This posts makes use of Springorg.springframework.core.convert.converter.Converter interface, which helps us with mapping Id’s of items to actual entities in database.

Following technologies being used:
-----------------------------------
- Spring 4.1.7.RELEASE
- Hibernate Core 4.3.10.Final
- validation-api 1.1.0.Final
- hibernate-validator 5.1.3.Final
- MySQL Server 5.6
- Maven 3
- JDK 1.8
- Tomcat 8.0.21
- Eclipse JUNO Service Release 2 or STS

APP_USER : Contains Users. A User can have several profiles[ USER,ADMIN,DBA].
USER_PROFILE : Contains User Profiles. A Profile can be linked to several users.
APP_USER_USER_PROFILE : It’s a Join table linking APP_USER & USER_PROFILE in Many-To-Many relationship.

For demonstration purpose, We will discuss Many-to-Many unidirectional [User to UserProfile] setup in this example.

create table APP_USER (
   id BIGINT NOT NULL AUTO_INCREMENT,
   sso_id VARCHAR(30) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   email VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (sso_id)
);
  
  
create table USER_PROFILE(
   id BIGINT NOT NULL AUTO_INCREMENT,
   type VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (type)
);
  
  
CREATE TABLE APP_USER_USER_PROFILE (
    user_id BIGINT NOT NULL,
    user_profile_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, user_profile_id),
    CONSTRAINT FK_APP_USER FOREIGN KEY (user_id) REFERENCES APP_USER (id),
    CONSTRAINT FK_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES USER_PROFILE (id)
);
 
/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE(type)
VALUES ('USER');
  
INSERT INTO USER_PROFILE(type)
VALUES ('ADMIN');
  
INSERT INTO USER_PROFILE(type)
VALUES ('DBA');
 
commit;


Look at how userProfiles property is annotated with ManyToMany.

@ManyToMany indicates that there is a Many-to-Many relationship between User and UserProfile. A User can have several profiles [ USER, ADMIN, DBA] while a profile can belong to several users. @JoinTable indicates that there is a link table which joins two tables using foreign key constraints to their primary keys. This annotation is mainly used on the owning side of the relationship. joinColumns refers to the column name of owning side(ID of USER), and inverseJoinColumns refers to the column of inverse side of relationship(ID of USER_PROFILE). Primary key of this joined table is combination of USER_ID & USER_PROFILE_ID.

Lazy Loading:
Pay special attention to fetch = FetchType.LAZY. Here we are informing hibernate to lazy load the userProfile collection. It’s also the default behavior. With this setup, a query to load the collection will be fired only when it is first accessed. It’s a good way to avoid fetching all connected object graph which is an expensive operation. When you are in transaction/active session, and will try to access collection, hibernate will fire separate select to fetch them.

But if you are outside active session (session closed/no transaction :you are in JSP e.g.), and tried to access the collection, you will meet your nemesis : org.hibernate.LazyInitializationException – could not initialize proxy – no Session. To avoid it, you need to initialize the collection on demand by calling Hibernate.initialize(user.getUserProfiles()); within an active session [you know the DAO method you were in, before coming all the way to view, you may call this initialize in that method.]

Also note that we are not using any cascade. It is because a userprofile is not dependent of user, and can live independently.


One important remark : In case of *Many* association, always override hashcode and equals method which are looked by hibernate when holding entities into collections.


