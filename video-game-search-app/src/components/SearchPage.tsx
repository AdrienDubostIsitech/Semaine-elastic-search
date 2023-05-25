import {useState} from "react";

interface VideoGame {
    GameName: string;
    Console: string;
    Review: string;
    Score: number;
}

function SearchPageComponent() {
    const [currentGames, setCurrentGames] = useState<VideoGame[]>([]);

    function getVideoGameByName(gameName: string) {
        console.log(gameName);
        if (gameName === null || gameName === "") {
            setCurrentGames([]);
            return;
        }
        fetch(`http://localhost:8080/api/v1/getGameByName/${gameName}`)
            .then(response => response.json())
            .then((data: VideoGame[]) => {
                setCurrentGames(data);
                console.log(data);
                console.log(currentGames);
            })
            .catch(error => {
                console.error(error);
            });
    }

    return (
        <div>
            <form>
                <div className="flex">
                    <div className="relative w-full">
                        <input onChange={(e) => getVideoGameByName(e.target.value)}
                               className="block p-2.5 w-full z-20 text-sm text-gray-900 bg-gray-50 rounded-full border-l-gray-50 border-l-2 border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-l-gray-700  dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500"
                               placeholder="Search video games by name"/>
                    </div>
                </div>
            </form>
            {currentGames.map((game, index) => (
                <div  key={index} className=" flex items-center justify-center pt-24">
                    <div
                        className="w-full max-w-md p-4 bg-white border border-gray-200 rounded-lg shadow sm:p-8 dark:bg-gray-800 dark:border-gray-700">
                        <div className="flex items-center justify-between mb-4">
                            <h5 className="text-xl font-bold leading-none text-gray-900 dark:text-white">Video Game
                                Infos</h5>
                        </div>
                        <div className="flow-root">
                            <ul role="list" className="divide-y divide-gray-200 dark:divide-gray-700">
                                <li className="py-3 sm:py-4">
                                    <div className="flex items-center space-x-4">
                                        <div className="flex-1 min-w-0">
                                            <p className="text-sm font-medium text-gray-900 truncate dark:text-white">
                                                Name :
                                            </p>
                                        </div>
                                        <div
                                            className="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                                            {game.GameName}
                                        </div>
                                    </div>
                                </li>
                                <li className="py-3 sm:py-4">
                                    <div className="flex items-center space-x-4">
                                        <div className="flex-1 min-w-0">
                                            <p className="text-sm font-medium text-gray-900 truncate dark:text-white">
                                                Console :
                                            </p>
                                        </div>
                                        <div
                                            className="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                                            {game.Console}
                                        </div>
                                    </div>
                                </li>
                                <li className="py-3 sm:py-4">
                                    <div className="flex items-center space-x-4">
                                        <div className="flex-1 min-w-0">
                                            <p className="text-sm font-medium text-gray-900 truncate dark:text-white">
                                                Review :
                                            </p>
                                        </div>
                                        <div
                                            className="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                                            {game.Review}
                                        </div>
                                    </div>
                                </li>
                                <li className="py-3 sm:py-4">
                                    <div className="flex items-center space-x-4">
                                        <div className="flex-1 min-w-0">
                                            <p className="text-sm font-medium text-gray-900 truncate dark:text-white">
                                                Score :
                                            </p>
                                        </div>
                                        <div
                                            className="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                                            {game.Score}
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            ))}

        </div>
    )
}

export default SearchPageComponent;