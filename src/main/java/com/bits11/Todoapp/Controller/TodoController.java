package com.bits11.Todoapp.Controller;

import com.bits11.Todoapp.entity.TodoEntity;
import com.bits11.Todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TodoController {


    private final TodoRepository todoRepository;

    //for multiple URI paths just use {"","/","/home"} else we can go with ("/")
    @GetMapping({"","/","/home"})
    public String showHomePage(Model model){
        model.addAttribute("todos", todoRepository.findAll());
    return "index";
    }

    //creating a handler method to add the task this wil be a post request
    @PostMapping("/add")
    public String add(@RequestParam String title){
        TodoEntity newTodo =TodoEntity.builder()
                                        .title(title)
                                        .completed(false)
                                        .build();
        todoRepository.save(newTodo);
        return "redirect:/";
    }

    //another method for updating means to cross the task which is done
    @GetMapping("/update/{id}")
    //used path variable to accept the id
    public String update(@PathVariable Long id){
    TodoEntity existingTodo=todoRepository.findById(id)
            //if we cant find it by id then it will throw exception instead it give a message
            .orElseThrow(()-> new RuntimeException("Todo not found: "+id));
    existingTodo.setCompleted(true);
    todoRepository.save(existingTodo);
    return "redirect:/";
    }

    //handler method for deleting the task
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id){
        TodoEntity existingTodo=todoRepository.findById(id)
                //if we cant find it by id then it will throw exception instead it give a message
                .orElseThrow(()-> new RuntimeException("Todo not found: "+id));
        todoRepository.delete(existingTodo);
        return "redirect:/";
    }
}
