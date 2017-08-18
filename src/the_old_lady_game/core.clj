(ns the-old-lady-game.core
  (require  [clojure.pprint :as pprint]
            [clojure.string :as s])
  (:gen-class))

(def board3x3
  [[0 1 2] [3 4 5] [6 7 8]])

(def board4x4
  [[0 1 2 3] [4 5 6 7] [8 9 10 11] [12 13 14 15]])

(defn visual-board [board]
    (str "\n" (s/join "\n\n" (map #(s/join "\t" %) board)) "\n\n"))

(defn position->dimensions [board position]
  "Returns the dimension tuple [row, column] based on position index"
  (let [width (Math/sqrt (count (flatten board)))
        row (int (Math/floor (/ position width)))
        column (int (mod position width))]
      (vector row column)))

(defn play [board position]
  (let [tuple (position->dimensions board (Integer. position))]
    (assoc-in board tuple "X")))

(defn receive-move []
  read-line)

(defn -main
  [& args]
  (let [player1 (rand-nth ["X" "O"])]
    (println "===== NEW GAME STARTED! =====\n" "Player" player1 ", pick a position by entering its number (Or \"quit\" to quit the game)")
    (println (visual-board board3x3))
    (loop [input (read-line) board board3x3]
    (when-not (= "quit" input)
      (println "New board: \n " (visual-board (play board input)))
      (recur (read-line) (play board input))))
      (println "\n ====== GAME OVER =====")))
