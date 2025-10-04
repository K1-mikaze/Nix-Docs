import os
import re
import datetime


def remove_emojis(text):
    """
    Elimina emojis y otros caracteres especiales del texto
    """
    # Patrón para detectar emojis y otros caracteres especiales
    emoji_pattern = re.compile(
        "["
        "\U0001F600-\U0001F64F"  # emoticones
        "\U0001F300-\U0001F5FF"  # símbolos y pictogramas
        "\U0001F680-\U0001F6FF"  # transporte y símbolos de mapas
        "\U0001F700-\U0001F77F"  # símbolos alquímicos
        "\U0001F780-\U0001F7FF"  # símbolos geométricos
        "\U0001F800-\U0001F8FF"  # símbolos suplementarios
        "\U0001F900-\U0001F9FF"  # símbolos suplementarios y pictogramas
        "\U0001FA00-\U0001FA6F"  # símbolos de ajedrez
        "\U0001FA70-\U0001FAFF"  # símbolos de emojis
        "\U00002702-\U000027B0"  # dingbats
        "\U000024C2-\U0001F251" 
        "]+", flags=re.UNICODE)
    
    return emoji_pattern.sub(r'', text)

def extract_file_info(file_path):
    """
    Extrae la información de un archivo
    """
    try:
        with open(file_path, 'r', encoding='utf-8', errors='ignore') as file:
            content = file.read()
            # Eliminar emojis
            content = remove_emojis(content)
            return content
    except Exception as e:
        return f"Error al leer el archivo {file_path}: {str(e)}"

def is_binary_file(file_path):
    """
    Verifica si un archivo es binario
    """
    # Extensiones comunes de archivos binarios
    binary_extensions = ['.png', '.jpg', '.jpeg', '.gif', '.bmp', '.ico', '.pdf', 
                         '.zip', '.jar', '.war', '.class', '.exe', '.dll', '.so']
    
    # Verificar por extensión
    _, ext = os.path.splitext(file_path.lower())
    if ext in binary_extensions:
        return True
    
    # Verificar contenido (primeros bytes)
    try:
        with open(file_path, 'rb') as f:
            chunk = f.read(1024)
            # Heurística simple: si hay muchos bytes nulos o caracteres no imprimibles, probablemente es binario
            textchars = bytearray({7, 8, 9, 10, 12, 13, 27} | set(range(0x20, 0x100)) - {0x7f})
            is_binary = bool(chunk.translate(None, textchars))
            return is_binary
    except:
        # Si hay error al leer, asumimos que es binario
        return True

def get_file_info(file_path):
    """
    Obtiene información básica del archivo
    """
    try:
        stats = os.stat(file_path)
        size = stats.st_size
        modified_time = datetime.datetime.fromtimestamp(stats.st_mtime).strftime('%Y-%m-%d %H:%M:%S')
        return f"Tamaño: {size} bytes, Última modificación: {modified_time}"
    except:
        return "No se pudo obtener información del archivo"

def extract_project_info(project_path, output_file):
    """
    Extrae toda la información del proyecto y la guarda en un archivo de texto
    """
    with open(output_file, 'w', encoding='utf-8') as out_file:
        # Escribir encabezado
        out_file.write("=" * 80 + "\n")
        out_file.write("INFORMACIÓN DEL PROYECTO NIX-DOCS\n")
        out_file.write("Fecha de extracción: " + datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S') + "\n")
        out_file.write("=" * 80 + "\n\n")
        
        # Recorrer todos los archivos del proyecto
        for root, dirs, files in os.walk(project_path):
            # Ignorar directorios ocultos y carpetas de dependencias
            if '.git' in root or 'node_modules' in root or 'target' in root:
                continue
                
            # Escribir información del directorio
            rel_path = os.path.relpath(root, project_path)
            if rel_path == '.':
                out_file.write("DIRECTORIO RAÍZ: " + project_path + "\n")
            else:
                out_file.write("\n" + "=" * 40 + "\n")
                out_file.write("DIRECTORIO: " + rel_path + "\n")
                out_file.write("=" * 40 + "\n")
            
            # Procesar archivos
            for file in files:
                file_path = os.path.join(root, file)
                rel_file_path = os.path.relpath(file_path, project_path)
                
                # Ignorar archivos binarios
                if is_binary_file(file_path):
                    out_file.write("\nARCHIVO (BINARIO): " + rel_file_path + "\n")
                    out_file.write(get_file_info(file_path) + "\n")
                    out_file.write("Contenido no extraído (archivo binario)\n")
                else:
                    out_file.write("\nARCHIVO: " + rel_file_path + "\n")
                    out_file.write(get_file_info(file_path) + "\n")
                    out_file.write("-" * 40 + "\n")
                    
                    # Extraer contenido
                    content = extract_file_info(file_path)
                    out_file.write(content + "\n")
                    out_file.write("-" * 40 + "\n")
        
        # Escribir pie de página
        out_file.write("\n\n" + "=" * 80 + "\n")
        out_file.write("FIN DE LA EXTRACCIÓN\n")
        out_file.write("=" * 80 + "\n")

if __name__ == "__main__":
    # Ruta del proyecto
    project_path = os.path.dirname(os.path.abspath(__file__))
    
    # Archivo de salida
    output_file = os.path.join(project_path, "proyecto_info.txt")
    
    print(f"Extrayendo información del proyecto en: {project_path}")
    print(f"Guardando en: {output_file}")
    
    extract_project_info(project_path, output_file)
    
    print("Extracción completada. La información se ha guardado en 'proyecto_info.txt'")