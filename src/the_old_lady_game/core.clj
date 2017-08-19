(ns the-old-lady-game.core
  (require  [clojure.pprint :as pprint]
            [clojure.string :as s])
  (:gen-class))

(def a-nought " O ")

(def a-cross " X ")

(def board3x3
  [["[0]" "[1]" "[2]"] ["[3]" "[4]" "[5]"] ["[6]" "[7]" "[8]"]])

(def board4x4
  [["[0]" "[1]" "[2]" "[3]"] ["[4]" "[5]" "[6]" "[7]"]
   ["[8]" "[9]" "[10]" "[11]"] ["[12]" "[13]" "[14]" "[15]"]])

(defn visual-board [board]
  "Visual representation on the board for terminal"
    (str "\n" (s/join "\n\n" (map #(s/join "\t" %) board)) "\n\n"))

(defn position->dimensions [board position]
  "Returns the dimension tuple [row, column] based on position index"
  (let [width (Math/sqrt (count (flatten board)))
        row (int (Math/floor (/ position width)))
        column (int (mod position width))]
      (vector row column)))

(defn valid-move? [board tuple]
  (not (or (= a-nought (get-in board tuple)) (= a-cross (get-in board tuple)))))

(defn play [board position]
  "Performs the move based on the current board and position provided"
  (let [tuple (position->dimensions board (Integer. position))
        flat-board (flatten board)
        noughts-count (count (filter #(= a-nought %) flat-board))
        crosses-count (count (filter #(= a-cross %) flat-board))
        player-symbol (if (> crosses-count noughts-count) a-nought a-cross)]
        (if (valid-move? board tuple)
              (assoc-in board tuple player-symbol)
              board)))
(defn -main
  [& args]
    (println "===== NEW GAME STARTED! =====\n"
             "Player 'X', pick a position by entering its number"
             " (or \"quit\" to quit the game)")
    (println (visual-board board3x3))
    (loop [input (read-line) board board3x3]
    (when-not (= "quit" input)
      (println "Current board: \n " (visual-board (play board input)))
      (recur (read-line) (play board input))))
      (println "\n ====== GAME OVER ====="))
