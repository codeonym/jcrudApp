function showSection(sectionId) {

  //  FUNCTION TO TOGGLE SIDEBAR

  // HIDE ALL SECTIONS
    const sections = document.querySelectorAll('main section');
    sections.forEach((section) => {
      section.classList.remove("flex");
      section.classList.add("hidden");
    });

  // SHOW THE SELECTED SECTION
    document.getElementById(sectionId).classList.remove("hidden");
    document.getElementById(sectionId).classList.add("flex");
}

function showModal(dialogId) {

  //SHOW POPUP
    const dialog = document.getElementById(dialogId);
  dialog.classList.remove('hidden');
  dialog.classList.add('flex');
  }

function closeModal(dialogId) {
    
  //CLOSE POPUP
    const dialog = document.getElementById(dialogId);
  dialog.classList.add('hidden');
  dialog.classList.remove('flex');
  }
onload = function () {

  // FIRST CALL
  showSection("showStudents");


    }