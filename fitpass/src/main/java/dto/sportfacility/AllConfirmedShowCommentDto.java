package dto.sportfacility;

import beans.sportfacility.Comment;

import java.util.List;

public class AllConfirmedShowCommentDto {
    private List<ShowCommentDto> allComments;

    public AllConfirmedShowCommentDto(List<ShowCommentDto> allComments) {
        this.allComments = allComments;
    }

    public List<ShowCommentDto> getAllComments() {
        return allComments;
    }

    public void setAllComments(List<ShowCommentDto> allComments) {
        this.allComments = allComments;
    }
}
