package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientSerivce {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

}
