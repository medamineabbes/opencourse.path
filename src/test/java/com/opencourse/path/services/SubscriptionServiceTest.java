package com.opencourse.path.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.opencourse.path.dtos.SubscribedPathDto;
import com.opencourse.path.dtos.mappers.PathMapper;
import com.opencourse.path.entities.Path;
import com.opencourse.path.entities.Project;
import com.opencourse.path.entities.Subscription;
import com.opencourse.path.entities.SubscriptionType;
import com.opencourse.path.entities.Topic;
import com.opencourse.path.repos.SubscriptionRepo;

public class SubscriptionServiceTest {
    
    private SubscriptionRepo subscriptionRepo=mock(SubscriptionRepo.class);
    private PathMapper pathMapper=new PathMapper();
    private SubscriptionService service;

    private Path path1,path2;
    private Project p1,p2,p3,p4,p5,p6,p7,p8;

    @BeforeEach
    public void init(){
        service=new SubscriptionService(subscriptionRepo,pathMapper);
        Topic topic=new Topic();
        topic.setName("name");

        path1=new Path();
        path2=new Path();

        p1=new Project();p3=new Project();p5=new Project();p7=new Project();
        p2=new Project();p4=new Project();p6=new Project();p8=new Project();

        p1.setCourseIds(List.of(1L,2L,3L,4L));
        p2.setCourseIds(List.of(5L,6L,7L,8L));
        p3.setCourseIds(List.of(12L,11L,10L,9L));
        p4.setCourseIds(List.of(13L,14L,15L,16L));
        p5.setCourseIds(List.of(17L,18L,19L,20L));
        p6.setCourseIds(List.of(21L,22L,23L,24L));
        p7.setCourseIds(List.of(15L,26L,27L,28L));
        p8.setCourseIds(List.of(29L,30L,40L,41L));

        path1.setProjects(List.of(p1,p2,p3,p4));
        path2.setProjects(List.of(p5,p6,p7,p8));
        p1.setPath(path1);
        p2.setPath(path1);
        p3.setPath(path1);
        p4.setPath(path1);

        p5.setPath(path2);
        p6.setPath(path2);
        p7.setPath(path2);
        p8.setPath(path2);
        topic.setPaths(List.of(path1,path2));
        path1.setTopic(topic);
        path2.setTopic(topic);
    }

    @Test
    @DisplayName("should return false")
    public void userHasAccesToCourseTest(){
        SubscriptionType type=new SubscriptionType();
        type.setDurationMonth(2);
        Subscription sub=new Subscription();
        sub.setDate(LocalDateTime.now().minusMonths(1));
        sub.setPath(path1);
        sub.setType(type);
        Subscription sub2=new Subscription();
        sub2.setDate(LocalDateTime.now().minusMonths(3));
        sub2.setPath(path2);
        sub2.setType(type);
        when(subscriptionRepo.findByUserId(15L)).thenReturn(List.of(sub,sub2));

        boolean access=service.userHasAccessToCourse(15L, 29L);
        assertFalse(access);
    }

    @Test
    @DisplayName("should return true")
    public void userHasAccessToCourseTest2(){
        SubscriptionType type=new SubscriptionType();
        type.setDurationMonth(2);
        Subscription sub=new Subscription();
        sub.setDate(LocalDateTime.now().minusMonths(1));
        sub.setPath(path1);
        sub.setType(type);
        Subscription sub2=new Subscription();
        sub2.setDate(LocalDateTime.now().minusMonths(1));
        sub2.setPath(path2);
        sub2.setType(type);
        when(subscriptionRepo.findByUserId(15L)).thenReturn(List.of(sub,sub2));

        boolean access=service.userHasAccessToCourse(15L, 29L);
        assertTrue(access);
    }

    @Test
    @DisplayName("should return empty")
    public void getPathBySubTest(){
        List<SubscribedPathDto> subPathDto=service.getPathsByUserSub(80L);
        assertEquals(0, subPathDto.size());
    }

    @Test
    @DisplayName("should return 1 path")
    public void getPathsBySubTest2(){
        LocalDateTime now=LocalDateTime.now();

        SubscriptionType type=new SubscriptionType();
        type.setDurationMonth(2);

        Subscription sub=new Subscription();
        sub.setDate(now.minusMonths(1));
        sub.setPath(path1);
        sub.setType(type);

        Subscription sub2=new Subscription();
        sub2.setDate(now.minusMonths(3));
        sub2.setPath(path2);
        sub2.setType(type);

        when(subscriptionRepo.findByUserId(15L)).thenReturn(List.of(sub,sub2));
        List<SubscribedPathDto> subs=service.getPathsByUserSub(15L);

        assertEquals(2, subs.size());
        assertEquals(path1.getTitle(),subs.get(0).getPathDto().getTitle());
        assertEquals(now.plusMonths(1), subs.get(0).getEndsAt());

        assertTrue(subs.get(0).getActiveSubscription());
        assertFalse(subs.get(1).getActiveSubscription());
    }

}
