import os

def show_project_structure(base_path):
    for root, dirs, files in os.walk(base_path):
        # Nivel de indentación según la profundidad
        level = root.replace(base_path, "").count(os.sep)
        indent = " " * 4 * level
        print(f"{indent}[DIR] {os.path.basename(root)}")

        for f in files:
            print(f"{indent}    [FILE] {f}")


# 👉 Ejemplo de uso
if __name__ == "__main__":
    project_path = "C:/Users/juand/OneDrive/Escritorio/other/This is me/version2/projects/Nix/Nix-Docs"
    show_project_structure(project_path)
