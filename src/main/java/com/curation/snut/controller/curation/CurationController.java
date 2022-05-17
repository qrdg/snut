package com.curation.snut.controller.curation;

import java.util.List;

import com.curation.snut.dto.curation.CurationDTO;

import com.curation.snut.service.MemberService;
import com.curation.snut.service.curation.CurationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/curation")
public class CurationController {
    private final CurationService curationService;

    @GetMapping("/list")
    public ResponseEntity<?> cuList(String searchCurationTitle) {

        if (searchCurationTitle != null) {
            List<CurationDTO> searchCurationList = curationService.searchCurationTitle(searchCurationTitle);
            return new ResponseEntity<>(searchCurationList, HttpStatus.OK);
        } else {
            List<CurationDTO> cuList = curationService.CuList();
            return new ResponseEntity<>(cuList, HttpStatus.OK);
        }

    };

    // @GetMapping("/write")
    // public ResponseEntity<?> write3(@AuthenticationPrincipal MemberDTO
    // memberDTO){
    // return new ResponseEntity<>(memberDTO,HttpStatus.OK);
    // } jwt 방식이라서 @AuthenticationPrincipal 을 안씀,

    @PostMapping("/write")
    public ResponseEntity<?> curationWrite2(CurationDTO curationDTO) {
        curationService.write(curationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping({ "/read", "/modify" })
    public ResponseEntity<?> read(Long curationNo) {
        CurationDTO curationDTO = curationService.getCuration(curationNo);
        return new ResponseEntity<>(curationDTO, HttpStatus.OK);
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modify(CurationDTO curationDTO) {
        curationService.modify(curationDTO);
        return new ResponseEntity<>(curationDTO.getCurationNo(), HttpStatus.OK);
    }

    @GetMapping({ "/list/delete", "/read/delete" })
    public ResponseEntity<?> delete(Long id) {
        curationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
