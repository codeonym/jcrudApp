<main class="mt-8 flex h-full justify-center items-center bg-white shadow-md shadow-slate-200 p-8">
    <div
            class="block md:flex container mx-auto items-center"
    >
        <div class="h-full w-full md:w-1/2 md:border-r-8 md:border-sky-400 pr-8">
            <h1 class="py-8 text-sky-400 text-4xl font-extrabold">Welcome<br \>To Master Portal
                <img class="w-36 inline-block animate-bounce" src="resources/assets/illustration2.svg" alt="">
            </h1>
            <p class="py-4 text-slate-400">
                Welcome to the Master Portal, a robust platform designed for teachers.
                Effortlessly manage and update students' data with precision and speed.
                Experience a new level of efficiency and organization in your academic tasks.
            </p>
            <img class="w-96" src="resources/assets/illustration1.svg" alt="">
            <p class="py-4 text-slate-400" >
                Explore the enhanced features of this platform, specially crafted for educators.
                New year, new look! The Master Portal is your go-to tool for seamless student data editing.
                Boost your productivity and make the most of your teaching experience.
            </p>
        </div>
        <div class="h-full w-full md:w-1/2">
            <div
                    class="py-4 px-12 m-4 text-2xl mx-auto w-fit text-slate-400 font-extrabold"
            >
                <h1 class="text-sky-400 transition-colors">Authenticate !</h1>
            </div>
            <form
                    action="login"
                    method="post"
                    class="transition-all p-4 flex flex-col space-y-4 items-center justify-center"
            >
                <label
                        for="username"
                        class="p-1 rounded-md flex bg-slate-50 fill-slate-500 focus-within:fill-sky-400 focus-within:border-sky-400 focus-within:ring-4 focus-within:text-sky-400 focus-within:ring-sky-200 w-80 md:w-96 items-center justify-center"
                >
                    <svg
                            class="mx-2"
                            width="24px"
                            height="24px"
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 448 512"
                    >
                        <!--! Font Awesome Free 6.4.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free (Icons: CC BY 4.0, Fonts: SIL OFL 1.1, Code: MIT License) Copyright 2023 Fonticons, Inc. -->
                        <path
                                d="M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z"
                        />
                    </svg>
                    <input
                            class="bg-inherit w-full p-2 outline-none placeholder:text-slate-300 focus:placeholder:text-sky-300"
                            placeholder="username"
                            type="text"
                            name="username"
                            id="username"
                            required=""
                    />
                </label>
                <label
                        for="password"
                        class="p-1 rounded-md flex bg-slate-50 fill-slate-500 focus-within:fill-sky-400 focus-within:border-sky-400 focus-within:ring-4 focus-within:text-sky-400 focus-within:ring-sky-200 w-80 md:w-96 items-center justify-center"
                >
                    <svg
                            class="mx-2"
                            width="24px"
                            height="24px"
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 512 512"
                    >
                        <!--! Font Awesome Free 6.4.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free (Icons: CC BY 4.0, Fonts: SIL OFL 1.1, Code: MIT License) Copyright 2023 Fonticons, Inc. -->
                        <path
                                d="M336 352c97.2 0 176-78.8 176-176S433.2 0 336 0S160 78.8 160 176c0 18.7 2.9 36.8 8.3 53.7L7 391c-4.5 4.5-7 10.6-7 17v80c0 13.3 10.7 24 24 24h80c13.3 0 24-10.7 24-24V448h40c13.3 0 24-10.7 24-24V384h40c6.4 0 12.5-2.5 17-7l33.3-33.3c16.9 5.4 35 8.3 53.7 8.3zM376 96a40 40 0 1 1 0 80 40 40 0 1 1 0-80z"
                        />
                    </svg>
                    <input
                            class="bg-inherit w-full p-2 outline-none placeholder:text-slate-300 focus:placeholder:text-sky-300"
                            type="password"
                            placeholder="password"
                            name="password"
                            id="password"
                            required=""
                    />
                </label>
                <label
                        for="auth"
                        class="p-1 flex w-80 md:w-96 items-center justify-center"
                >
                    <input
                            class="w-full transition-all text-sky-400 rounded-full hover:bg-sky-400 p-3 outline-none border-sky-400 border-2 hover:text-white hover:ring-4 hover:ring-sky-200"
                            type="submit"
                            name="auth"
                            id="auth"
                            value="login"
                    />
                </label>
            </form>
        </div>
    </div>
</main>
</body>
</html>
