package com.a1996.ben.collectivecookbook.Model;

/**
 * Created by Ben on 6/28/2016.
 */
public class SimpleRecipeName {

    private Recipe recipe;
    private String objectId;
    private String recipeName;
    private int votes;

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

    public SimpleRecipeName() {}

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
