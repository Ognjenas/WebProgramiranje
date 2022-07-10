package dto.sportfacility;

import java.util.List;

public class AllCommentsAdminDto {

    List<ShowCommentAdminDto> allComments;

    public AllCommentsAdminDto(List<ShowCommentAdminDto> allComments) {
        this.allComments = allComments;
    }

    public List<ShowCommentAdminDto> getAllComments() {
        return allComments;
    }

    public void setAllComments(List<ShowCommentAdminDto> allComments) {
        this.allComments = allComments;
    }
}
