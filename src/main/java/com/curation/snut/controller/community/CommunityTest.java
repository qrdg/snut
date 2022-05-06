package com.curation.snut.controller.community;

import java.util.List;

import com.curation.snut.dto.community.CommunityDTO;
import com.curation.snut.service.community.CommentService;
import com.curation.snut.service.community.CommunityService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping
@Log4j2
@RequiredArgsConstructor
public class CommunityTest {
    private final CommunityService communityService;
    private final CommentService commentService;

    @GetMapping("/communityList")
    public String CList(Model model, String searchTitle) {
        if (searchTitle != null) {
//            List<CommunityDTO> searchCommunityList = communityService.searchTitle(searchTitle);
//            model.addAttribute("cList", searchCommunityList);
        } else {
//            List<CommunityDTO> list = communityService.communityListWithCnt();
//            model.addAttribute("cList", list);
        }
        return "communityList.html";
    }

    @PostMapping("/communityList")
    public String commentWrite(CommunityDTO communityDTO) {
        log.info("writed......." + communityDTO);
        communityService.write(communityDTO);
        return "redirect:/communityList";
    }

    @GetMapping("/communityList/delete")
    public String delete(Long id) {
        commentService.deleteAllCommunutyComment(id);
        communityService.delete(id);
        return "redirect:/communityList";
    }

}
