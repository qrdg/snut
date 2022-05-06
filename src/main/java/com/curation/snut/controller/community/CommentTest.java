package com.curation.snut.controller.community;

import com.curation.snut.dto.community.CommentDTO;
import com.curation.snut.service.community.CommentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
// import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping
@Log4j2
@RequiredArgsConstructor
public class CommentTest {

    private final CommentService commentService;

    @GetMapping("/comment")
    public String comment(Model model,
            @PageableDefault(size = 2) Pageable pageable,
            Long no) {
        log.info("comment......");
        Page<CommentDTO> commentDTOList = commentService.commentList(pageable, no);
        model.addAttribute("commentList", commentDTOList);

        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, commentDTOList.getTotalPages());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("cnt", commentService.commnetCnt(no));

        return "comment.html";
    }

    @PostMapping("/comment")
    public String commentWrite(CommentDTO commentDTO) {
        log.info("writed......." + commentDTO);
        log.info("writed2......." + commentDTO.getCommunityName().getNo());
        Long no = commentDTO.getCommunityName().getNo();
        commentService.write(commentDTO);
        return "redirect:/comment?no=" + no;
    }

    @GetMapping("/comment/delete")
    public String delete(Long id) {
        commentService.delete(id);
        return "redirect:/comment";
    }

}
