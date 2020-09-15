import cv2
import datetime

cap = cv2.VideoCapture('./video/01-03826.mp4')


width = cap.get(cv2.CAP_PROP_FRAME_WIDTH)
height = cap.get(cv2.CAP_PROP_FRAME_HEIGHT)

while cap.isOpened:
    ret, frame = cap.read()

    if ret:
        cv2.imshow("video", frame)

        key = cv2.waitKey(33)
        now = datetime.datetime.now().strftime("%d_%H-%M-%S")

        # esc 키를 누르면 종료
        if key == 27:
            break
        # ctrl + z
        elif key == 26:
            cv2.IMREAD_UNCHANGED
            img_path = "./cap_" + now + ".jpg"
            cv2.imwrite(img_path, frame)

        # elif key == 39:


    else:
        break
cap.release()

cv2.destroyAllWindows()