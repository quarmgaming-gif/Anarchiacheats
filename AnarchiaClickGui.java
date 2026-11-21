package pl.anarchia.cheats;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnarchiaClickGui extends Screen {
    private static final int PURPLE = 0xFF9B35FF;
    private static final int DARK = 0xE8100820;
    private static final int PANEL = 0xE91A102B;
    private static final Map<String, Boolean> MODULES = new LinkedHashMap<>();
    private String category = "Combat";

    static {
        for (String name : new String[]{"KillAura", "Reach", "AutoClicker", "Criticals", "Velocity", "AntiBot",
                "Speed", "Fly", "Jesus", "LongJump", "NoSlow", "Sprint", "ESP", "Tracers", "ChestESP",
                "NameTags", "Fullbright", "PlayerESP", "AutoGG", "AntiAFK", "Timer", "AutoReconnect", "NoFall"}) {
            MODULES.put(name, false);
        }
    }

    public AnarchiaClickGui() {
        super(Text.literal("Anarchia Cheats"));
    }

    @Override
    protected void init() {
        clearChildren();
        int left = Math.max(12, (width - 560) / 2);
        int top = Math.max(12, (height - 330) / 2);
        String[] categories = {"Combat", "Movement", "Player", "Render", "Misc", "Settings"};
        for (int i = 0; i < categories.length; i++) {
            final String selected = categories[i];
            addDrawableChild(ButtonWidget.builder(Text.literal((selected.equals(category) ? "» " : "") + selected), b -> {
                category = selected;
                init();
            }).dimensions(left + 12, top + 58 + i * 39, 132, 32).build());
        }
        String[] entries = modulesFor(category);
        int colWidth = 190;
        for (int i = 0; i < entries.length; i++) {
            String module = entries[i];
            int col = i / 6;
            int row = i % 6;
            int x = left + 158 + col * 196;
            int y = top + 58 + row * 39;
            boolean enabled = MODULES.getOrDefault(module, false);
            addDrawableChild(ButtonWidget.builder(Text.literal(module + "   " + (enabled ? "ON" : "OFF")), b -> {
                MODULES.put(module, !MODULES.getOrDefault(module, false));
                init();
            }).dimensions(x, y, colWidth, 32).build());
        }
        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), b -> close())
                .dimensions(left + 468, top + 12, 88, 28).build());
    }

    private String[] modulesFor(String cat) {
        return switch (cat) {
            case "Combat" -> new String[]{"KillAura", "Reach", "AutoClicker", "Criticals", "Velocity", "AntiBot"};
            case "Movement" -> new String[]{"Speed", "Fly", "Jesus", "LongJump", "NoSlow", "Sprint"};
            case "Render" -> new String[]{"ESP", "Tracers", "ChestESP", "NameTags", "Fullbright", "PlayerESP"};
            case "Misc" -> new String[]{"AutoGG", "AntiAFK", "Timer", "AutoReconnect", "NoFall"};
            case "Player" -> new String[]{"Settings coming soon"};
            default -> new String[]{"ClickGUI", "Accent: Purple", "Right Shift: Open GUI"};
        };
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        int left = Math.max(12, (width - 560) / 2);
        int top = Math.max(12, (height - 330) / 2);
        context.fill(left, top, left + 568, top + 338, DARK);
        context.fill(left, top, left + 568, top + 3, PURPLE);
        context.fill(left, top + 44, left + 568, top + 46, PURPLE);
        context.fill(left + 150, top + 46, left + 152, top + 338, PURPLE);
        context.drawText(textRenderer, "ANARCHIA", left + 14, top + 10, 0xFFFFFFFF, true);
        context.drawText(textRenderer, "CHEATS", left + 90, top + 10, PURPLE, true);
        context.drawText(textRenderer, "1.0.0  •  " + category, left + 14, top + 29, 0xFFBFA6D9, false);
        context.drawText(textRenderer, "Purple ClickGUI prototype", left + 164, top + 29, 0xFFBFA6D9, false);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
