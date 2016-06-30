package com.a1996.ben.collectivecookbook.Model;

/**
 * Created by Ben on 6/28/2016.
 */
public class Recipe {
    private String objectId;
    private String recipeName;
    private String directions;
    private String ingredients;
    private int votes;

    public Recipe() {}

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public void setObjectId( String objectId )
    {
        this.objectId = objectId;
    }
}
