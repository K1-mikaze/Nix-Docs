/* Button Language */
function getFilename() {
  var rutaAbsoluta = self.location.href;
  var posicionUltimaBarra = rutaAbsoluta.lastIndexOf("/");
  var rutaRelativa = rutaAbsoluta.substring(posicionUltimaBarra + 1);
  return rutaRelativa;
}

function parseLanguage(filename) {
  var lastlesssign = filename.lastIndexOf("-");
  var filenameNotLangSpecificator = filename.substring(0, lastlesssign);
  var languageSpecificator = filename.substring(lastlesssign + 1);
  if (languageSpecificator === "ES.html") {
    return `${filenameNotLangSpecificator}-EN.html`;
  } else if (languageSpecificator === "EN.html") {
    return `${filenameNotLangSpecificator}-ES.html`;
  }
}

function setDiferentLanguageFile(filePath) {
  var link = document.getElementById("language_link");
  link.href = filePath;
}

/* -------------------------------------------------*/

function generateSpecificLanguageLinks(files, folder) {
  for (let file = 0; file < files.length; file++) {
    folder.innerHTML += `<li><a href="${files[file].path}">${files[file].name}</a></li>`;
  }
}

const folders = [
  document.getElementById("nix_Language"),
  document.getElementById("nixPkgs"),
  document.getElementById("nixOS"),
  document.getElementById("build_Nix"),
  document.getElementById("community"),
  document.getElementById("about_Nix"),
];

const mapa = new Map([
  ["file1", "#"],
  ["file2", "#"],
  ["file3", "#"],
]);

mapa.forEach((value, key) => {
  folders[0].innerHTML += `<li><a href="${value}">${key}</a></li>`;
});

setDiferentLanguageFile(parseLanguage(getFilename()));
