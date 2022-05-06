package com.curation.snut.service.memberlike;

public interface MemberCurationLikeService {

    public void likes(Long mcolId, String email);
    public void unlikes(Long mcolId, String email);
}
