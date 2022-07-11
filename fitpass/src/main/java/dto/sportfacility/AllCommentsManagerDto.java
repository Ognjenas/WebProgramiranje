package dto.sportfacility;

import java.util.List;

public class AllCommentsManagerDto {
    List<ShowCommentManagerDto> allComments;

    public AllCommentsManagerDto(List<ShowCommentManagerDto> allComments) {
        this.allComments = allComments;
    }

    public List<ShowCommentManagerDto> getAllComments() {
        return allComments;
    }

    public void setAllComments(List<ShowCommentManagerDto> allComments) {
        this.allComments = allComments;
    }
}
