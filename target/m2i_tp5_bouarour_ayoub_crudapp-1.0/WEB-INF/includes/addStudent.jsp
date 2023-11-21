<section id="addStudent" class="hidden">
    <!-- Add Student Form  -->
    <form action="studentAction" method="post" class="mt-8">
        <h3 class="text-2xl font-semibold mb-4">Add New Student</h3>
        <div class="flex flex-col space-y-2">
            <div class="mt-4">
                <label for="addNom" class="block mb-2 text-sm font-medium text-gray-600">Nom:</label>
                <input type="text" id="addNom" name="addNom" class="form-input border-2 py-2 px-4 w-64 md:w-96"
                       required />
            </div>

            <div class="mt-4">
                <label for="addPrenom" class="block mb-2 text-sm font-medium text-gray-600">Prenom:</label>
                <input type="text" id="addPrenom" name="addPrenom" class="form-input border-2 py-2 px-4 w-64 md:w-96"
                       required />
            </div>
        </div>

        <div class="flex flex-col space-y-2">
            <div class="mt-4">
                <label for="addCne" class="block mb-2 text-sm font-medium text-gray-600">Cne:</label>
                <input type="text" id="addCne" name="addCne" class="form-input border-2 py-2 px-4 w-64 md:w-96"
                       required />
            </div>

            <div class="mt-4">
                <label for="addDateNaissance" class="block mb-2 text-sm font-medium text-gray-600">Date Of
                    Birth:</label>
                <input type="date" id="addDateNaissance" name="addDateNaissance"
                       class="form-input border-2 py-2 px-4 w-64 md:w-96" required />
            </div>
        </div>

        <div class="mt-4">
            <label for="addAdresse" class="block mb-2 text-sm font-medium text-gray-600">Adresse:</label>
            <textarea name="addAdresse" id="addAdresse" cols="30" rows="10"
                      class="border-2 py-2 px-4 w-64 min-h-36 max-h-36 md:w-96" required></textarea>
        </div>

        <input type="hidden" name="action" value="add_student">
        <button type="submit"
                class="bg-teal-400 text-white px-6 py-2 mt-6 rounded hover:bg-teal-500 transition duration-300 ease-in-out">Add
            Student</button>
    </form>

</section>