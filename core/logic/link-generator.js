var Nix_Language = document.getElementById("Nix_Language");
var About_Nix = document.getElementById("About_Nix");

let array = ["1", "2", "3", "4", "5"];

for (let i = 0; i < array.length; i++) {
  Nix_Language.innerHTML += `<li><a href="#">${array[i]}</a></li>`;
}

About_Nix.innerHTML += `<li><a href="../view/contact.html">Contact</a></li>`;
