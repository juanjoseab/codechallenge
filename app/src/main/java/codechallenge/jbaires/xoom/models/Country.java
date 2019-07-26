package codechallenge.jbaires.xoom.models;

public class Country {
    private Integer id;
    private String name;
    private  String code;
    private Integer isFavorite;

    public Country() {
    }

    public Country(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.isFavorite = 0;
    }

    public Country(Integer id, String name, String code, Integer isFavorite) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.isFavorite = isFavorite;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer isFavorite() {
        return this.isFavorite;
    }

    public void setFavorite(Integer favorite) {
        this.isFavorite = favorite;
    }

    public boolean isFavoriteCountry(){

        if(this.isFavorite  != null){
            if(this.isFavorite == 1){
                return true;
            }
        }
        return false;
    }

    public void setAsFavoriteCountry(boolean check){

        if(check == true){
            this.isFavorite = 1;
        } else {
            this.isFavorite = 0;
        }
    }

    public void setIsFavorite(Integer isFavorite) {
        this.isFavorite = isFavorite;
    }
}
