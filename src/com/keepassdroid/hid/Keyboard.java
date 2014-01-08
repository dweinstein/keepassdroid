package com.keepassdroid.hid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dweinstein on 5/26/13.
 */
public class Keyboard {

    public static boolean buildAndExecuteKeyboardActionsFromString(String str) {

        for (int index = 0; index < str.length(); index++) {
            ExecutableKeyboardAction act = buildExecutableKeyboardActionFromKeystroke(str
                    .charAt(index));
            act.execute();
        }

        return true;
    }

    private static ExecutableKeyboardAction buildKeyboardActionFromChar(char character) {

        switch (character) {
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
                return keyAction(String.valueOf(character), null);

            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return keyAction(String.valueOf(character).toLowerCase(), "shift");

            case '`':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
            case '-':
            case '=':
            case '[':
            case ']':
            case '\\':
            case ';':
            case '\'':
            case ',':
            case '.':
            case '/':
            case ' ':
            case '\t':
            case '\n':
                return keyAction(String.valueOf(character), null);

            case '~':
                return keyAction("`", "shift");
            case '!':
                return keyAction("1", "shift");
            case '@':
                return keyAction("2", "shift");
            case '#':
                return keyAction("3", "shift");
            case '$':
                return keyAction("4", "shift");
            case '%':
                return keyAction("5", "shift");
            case '^':
                return keyAction("6", "shift");
            case '&':
                return keyAction("7", "shift");
            case '*':
                return keyAction("8", "shift");
            case '(':
                return keyAction("9", "shift");
            case ')':
                return keyAction("0", "shift");
            case '_':
                return keyAction("-", "shift");
            case '+':
                return keyAction("=", "shift");
            case '{':
                return keyAction("[", "shift");
            case '}':
                return keyAction("]", "shift");
            case '|':
                return keyAction("\\", "shift");
            case ':':
                return keyAction(";", "shift");
            case '"':
                return keyAction("'", "shift");
            case '<':
                return keyAction(",", "shift");
            case '>':
                return keyAction(".", "shift");
            case '?':
                return keyAction("/", "shift");
        }

        return null;
    }

    private static ExecutableKeyboardAction keyAction(String key, String modifier) {
        KeyboardAction act = new KeyboardAction();
        List<String> modifiers = new ArrayList<String>();
        act.setKey(key);
        if (modifier != null) {
            modifiers.add(modifier);
            act.setModifiers(modifiers);
        }

        return new ExecutableKeyboardAction(act);
    }

    private static ExecutableKeyboardAction buildExecutableKeyboardActionFromKeystroke(
            char key) {
        return buildKeyboardActionFromChar(key);
    }


}
