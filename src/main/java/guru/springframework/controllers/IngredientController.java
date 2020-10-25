package guru.springframework.controllers;


import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientSerivce;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientSerivce ingredientSerivce;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientSerivce ingredientSerivce, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientSerivce = ingredientSerivce;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        model.addAttribute("recipe", recipeService.findCommandById(new Long(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        log.debug("Getting ingredient for recipe id: " + recipeId + "ingredient Id: " + ingredientId);

        model.addAttribute("ingredient", ingredientSerivce.findByRecipeIdAndIngredientId(new Long(recipeId), Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){

        log.debug("Show the ingredientform for RecipeId: " + recipeId + "IngredientId:" + ingredientId);

        model.addAttribute("ingredient", ingredientSerivce.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));

        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){

        log.debug("SaveOrUpdate RecipeId: " + ingredientCommand.getRecipeId() + "IngredientId:"
                + ingredientCommand.getId());

        IngredientCommand savedIngredientCommand = ingredientSerivce.saveIngredientCommand(ingredientCommand);

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/"
                + savedIngredientCommand.getId() + "/show";
    }



}
