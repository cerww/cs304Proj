import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

//import java.util.concurrent.Flow;


public class TopMenu extends JPanel {
    private Program mainProgram;


    public TopMenu(Program pm) {
        setLayout(new FlowLayout());
        mainProgram = pm;

        System.out.println(StateFactory.class.getMethods().length);
        Stream.of(StateFactory.class.getMethods())
                .filter(m->Modifier.isStatic(m.getModifiers()))
                .filter(m->m.isAnnotationPresent(ButtonOption.class))
                .sorted(Comparator.comparing(Method::getName))//TODO make this better, this comparison is effectivly random
                .forEach(method->{
                    var annotation = method.getAnnotation(ButtonOption.class);
                    JButton button = new JButton(annotation.buttonText());
                    button.addActionListener(actionListenerFor((a)-> {
                        try {
                            return (DisplayTableSelction) method.invoke(null,a);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }));
                    add(button);
                });



    }

    private ActionListener actionListenerFor(Function<Program,DisplayTableSelction> f){
        return (e)->mainProgram.changeState(f.apply(mainProgram));
    }

}
//elections, countries, municiples, provinces, policies, candidates ,electoral districts, donations
//parties, leaders, campiegn events