package aopcachetest.dto;

import aopcachetest.entity.UserPO;

public class CompanyDTO {
    private Long id;
    private UserPO userPO;
    private String companyName;

    public CompanyDTO(Long id, UserPO userPO, String companyName) {
        this.id = id;
        this.userPO = userPO;
        this.companyName = companyName;
    }

    public CompanyDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserPO getUserPO() {
        return userPO;
    }

    public void setUserPO(UserPO userPO) {
        this.userPO = userPO;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", userPO=" + userPO +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
