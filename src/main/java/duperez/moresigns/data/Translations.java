package duperez.moresigns.data;

import duperez.moresigns.common.variant.CanvasVariant;
import duperez.moresigns.common.variant.CanvasVariants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Every piece of text the mod shows, in English, Portuguese and Spanish, in one table so a key can never be
 * added in one language and forgotten in another. English is the default for any other language.
 */
final class Translations {
    /** Language of a column: its position in {@link Entry#text()}. */
    enum Language {
        ENGLISH, PORTUGUESE, SPANISH
    }

    record Entry(String key, String[] text) {
        String in(Language language) {
            return text[language.ordinal()];
        }
    }

    private static final Map<String, String[]> DYE_NAMES = Map.ofEntries(
            // English, Portuguese (agrees with "placa"), Spanish (agrees with "cartel")
            Map.entry("white", new String[]{"White", "Branca", "Blanco"}),
            Map.entry("orange", new String[]{"Orange", "Laranja", "Naranja"}),
            Map.entry("magenta", new String[]{"Magenta", "Magenta", "Magenta"}),
            Map.entry("light_blue", new String[]{"Light Blue", "Azul-clara", "Azul claro"}),
            Map.entry("yellow", new String[]{"Yellow", "Amarela", "Amarillo"}),
            Map.entry("lime", new String[]{"Lime", "Verde-limão", "Lima"}),
            Map.entry("pink", new String[]{"Pink", "Rosa", "Rosa"}),
            Map.entry("gray", new String[]{"Gray", "Cinza", "Gris"}),
            Map.entry("light_gray", new String[]{"Light Gray", "Cinza-clara", "Gris claro"}),
            Map.entry("cyan", new String[]{"Cyan", "Ciano", "Cian"}),
            Map.entry("purple", new String[]{"Purple", "Roxa", "Morado"}),
            Map.entry("blue", new String[]{"Blue", "Azul", "Azul"}),
            Map.entry("brown", new String[]{"Brown", "Marrom", "Marrón"}),
            Map.entry("green", new String[]{"Green", "Verde", "Verde"}),
            Map.entry("red", new String[]{"Red", "Vermelha", "Rojo"}),
            Map.entry("black", new String[]{"Black", "Preta", "Negro"}));

    private Translations() {
    }

    /** Name of a standing canvas sign (and its item) in each language. */
    static String[] signName(CanvasVariant variant) {
        if (variant.key() == null) {
            return new String[]{"Canvas Sign", "Placa de Tela", "Cartel de lienzo"};
        }
        String[] dye = DYE_NAMES.get(variant.dye() == null ? "" : variant.dye());
        if (dye != null) {
            return new String[]{dye[0] + " Canvas Sign", "Placa de Tela " + dye[1], "Cartel de lienzo " + dye[2].toLowerCase()};
        }
        if (variant == CanvasVariants.ORANGE_PURPLE) {
            return new String[]{variant.displayName(), "Placa de Tela Roxa e Laranja", "Cartel de lienzo morado y naranja"};
        }
        if (variant == CanvasVariants.GREEN_GRADIENT) {
            return new String[]{variant.displayName(), "Placa de Tela em Tons de Verde", "Cartel de lienzo en tonos verdes"};
        }
        return new String[]{variant.displayName(), variant.displayName(), variant.displayName()};
    }

    static List<Entry> fixed() {
        List<Entry> t = new ArrayList<>();
        add(t, "itemGroup.moresignstab", "More Signs", "More Signs", "More Signs");
        add(t, "item.moresigns.canvas", "Canvas", "Tela", "Lienzo");

        add(t, "block.moresigns.sign_workbench", "Sign Workbench", "Bancada de Placas", "Mesa de carteles");
        add(t, "block.moresigns.custom_sign", "Custom Sign", "Placa Personalizada", "Cartel personalizado");
        add(t, "item.moresigns.sign_frame", "Sign Frame", "Moldura de Placa", "Marco de cartel");
        add(t, "item.moresigns.sign_board", "Sign Board", "Painel de Placa", "Panel de cartel");
        add(t, "item.moresigns.sign_frame.named", "%s Sign Frame", "Moldura de %s", "Marco de %s");
        add(t, "item.moresigns.sign_board.named", "%s Sign Board", "Painel de %s", "Panel de %s");
        add(t, "moresigns.part.painted", "%1$s %2$s", "%2$s (%1$s)", "%2$s (%1$s)");
        add(t, "moresigns.tooltip.frame", "Frame: %s", "Moldura: %s", "Marco: %s");
        add(t, "moresigns.tooltip.board", "Board: %s", "Painel: %s", "Panel: %s");

        add(t, "container.moresigns.sign_workbench", "Sign Workbench", "Bancada de Placas", "Mesa de carteles");
        add(t, "moresigns.workbench.frame", "Frame", "Moldura", "Marco");
        add(t, "moresigns.workbench.board", "Board", "Painel", "Panel");

        add(t, "moresigns.workbench.tab.make", "Make parts", "Fabricar peças", "Fabricar piezas");
        add(t, "moresigns.workbench.tab.make.tooltip", "Turn a block into a frame or a board",
                "Transforme um bloco em moldura ou painel", "Convierte un bloque en marco o panel");
        add(t, "moresigns.workbench.tab.paint", "Paint", "Pintar", "Pintar");
        add(t, "moresigns.workbench.tab.paint.tooltip", "Tint a frame or a board with a dye",
                "Tinja uma moldura ou painel com corante", "Tiñe un marco o panel con tinte");
        add(t, "moresigns.workbench.tab.assemble", "Assemble", "Montar", "Montar");
        add(t, "moresigns.workbench.tab.assemble.tooltip", "Join a frame and a board into a sign",
                "Una uma moldura e um painel em uma placa", "Une un marco y un panel en un cartel");
        add(t, "moresigns.workbench.tab.disassemble", "Take apart", "Desmontar", "Desmontar");
        add(t, "moresigns.workbench.tab.disassemble.tooltip", "Get the frame and the board of a sign back",
                "Recupere a moldura e o painel de uma placa", "Recupera el marco y el panel de un cartel");

        add(t, "moresigns.workbench.slot.material", "Material", "Material", "Material");
        add(t, "moresigns.workbench.slot.part", "Part", "Peça", "Pieza");
        add(t, "moresigns.workbench.slot.dye", "Dye", "Corante", "Tinte");
        add(t, "moresigns.workbench.slot.dye.tooltip", "The dye is not used up", "O corante não é gasto", "El tinte no se gasta");
        add(t, "moresigns.workbench.slot.frame", "Frame", "Moldura", "Marco");
        add(t, "moresigns.workbench.slot.board", "Board", "Painel", "Panel");
        add(t, "moresigns.workbench.slot.sign", "Sign", "Placa", "Cartel");

        add(t, "moresigns.workbench.button.frame", "Make frames", "Fazer molduras", "Hacer marcos");
        add(t, "moresigns.workbench.button.board", "Make boards", "Fazer painéis", "Hacer paneles");
        add(t, "moresigns.workbench.button.cost", "%1$s per %2$s block", "%1$s por %2$s bloco", "%1$s por %2$s bloque");
        add(t, "moresigns.workbench.button.wash", "Wash off the paint", "Lavar a pintura", "Lavar la pintura");
        add(t, "moresigns.workbench.button.wash.on", "Washing: take the paint off", "Lavando: remove a pintura", "Lavando: quita la pintura");

        add(t, "moresigns.workbench.hint.make.insert", "Place a block to start", "Coloque um bloco para começar", "Coloca un bloque para empezar");
        add(t, "moresigns.workbench.hint.make.more", "Not enough material", "Material insuficiente", "Material insuficiente");
        add(t, "moresigns.workbench.hint.make.ready", "%2$s ×%1$s for %3$s block", "%2$s ×%1$s por %3$s bloco", "%2$s ×%1$s por %3$s bloque");
        add(t, "moresigns.workbench.hint.paint.insert", "Place a frame or a board", "Coloque uma moldura ou painel", "Coloca un marco o panel");
        add(t, "moresigns.workbench.hint.paint.dye", "Add a dye. It is not used up", "Adicione um corante. Ele não é gasto", "Añade un tinte. No se gasta");
        add(t, "moresigns.workbench.hint.paint.same", "It is already this color", "Já está dessa cor", "Ya es de este color");
        add(t, "moresigns.workbench.hint.paint.ready", "Paints it. The dye is kept", "Pinta. O corante é mantido", "Lo pinta. El tinte se conserva");
        add(t, "moresigns.workbench.hint.wash.clean", "No paint to wash off", "Sem pintura para lavar", "No hay pintura que lavar");
        add(t, "moresigns.workbench.hint.wash.ready", "Washes the paint off", "Lava a pintura", "Lava la pintura");
        add(t, "moresigns.workbench.hint.assemble.insert", "Place a frame and a board", "Coloque uma moldura e um painel", "Coloca un marco y un panel");
        add(t, "moresigns.workbench.hint.assemble.frame", "Add a frame", "Adicione uma moldura", "Añade un marco");
        add(t, "moresigns.workbench.hint.assemble.board", "Add a board", "Adicione um painel", "Añade un panel");
        add(t, "moresigns.workbench.hint.assemble.ready", "Joins them into a sign", "Junta tudo em uma placa", "Los une en un cartel");
        add(t, "moresigns.workbench.hint.disassemble.insert", "Place a custom sign", "Coloque uma placa personalizada", "Coloca un cartel personalizado");
        add(t, "moresigns.workbench.hint.disassemble.ready", "Gives the frame and the board", "Devolve a moldura e o painel", "Devuelve el marco y el panel");
        return t;
    }

    private static void add(List<Entry> table, String key, String english, String portuguese, String spanish) {
        table.add(new Entry(key, new String[]{english, portuguese, spanish}));
    }
}
