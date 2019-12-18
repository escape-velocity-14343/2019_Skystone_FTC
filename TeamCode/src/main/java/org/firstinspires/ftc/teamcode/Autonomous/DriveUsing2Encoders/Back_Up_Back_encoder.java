package org.firstinspires.ftc.teamcode.Autonomous.DriveUsing2Encoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DriveTrainAndPID.FourEncoderDriveTrain;
import org.firstinspires.ftc.teamcode.DriveTrainAndPID.TwoEncoderDriveTrain;

//Back up Auton that goes to the wall side of the bridge, and parks there

@Autonomous (name = "Back_Up_Back_TODOTTESTUNLESSYOUDONTWANTERRORS")
//@Disabled
public class Back_Up_Back_encoder extends LinearOpMode {
    //initializaing the future variables
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor LFMotor, LBMotor, RFMotor, RBMotor, clawMotor;
    private DigitalChannel limitSwitch;
    private Servo rotateServo, clawServo;
    private FourEncoderDriveTrain drive;

    //no. of ticks per one revolution of the yellow jacket motors
    int Ticks_Per_Rev = 1316;

    @Override
    public void runOpMode(){
        // Initialize the hardware variables.
        LFMotor  = hardwareMap.get(DcMotor.class, "LF Motor");
        LBMotor  = hardwareMap.get(DcMotor.class, "LB Motor");
        RFMotor  = hardwareMap.get(DcMotor.class, "RF Motor");
        RBMotor  = hardwareMap.get(DcMotor.class, "RB Motor");
        clawMotor = hardwareMap.get(DcMotor.class,"Claw Up Motor");
        limitSwitch = hardwareMap.get(DigitalChannel.class, "Limit Stop");
        rotateServo = hardwareMap.get(Servo.class, "Rotate Servo");
        clawServo = hardwareMap.get(Servo.class, "Claw Servo");

        LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        //Wheels on the robot funtions
        drive = new FourEncoderDriveTrain(LFMotor, LBMotor, RFMotor, RBMotor);

        //Reverse the right motors to move forward based on their orientation on the robot
        clawMotor.setDirection(DcMotor.Direction.REVERSE);
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
        rotateServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setDirection(Servo.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Mode", "Init");
        telemetry.update();
        runtime.reset();
        waitForStart();

        //Running the code
        LFMotor.getCurrentPosition();
        if (opModeIsActive()) {
            telemetry.addData("Going Forward","");
            telemetry.update();
            drive.DriveForwardDistance(1,12);
            telemetry.addData("Going Backward","");
            telemetry.update();
            drive.DriveBackwardDistance(1,12);
            telemetry.addData("Going Right","");
            telemetry.update();
            drive.StrafeRightDistance(1,12);
            telemetry.addData("Going Left","");
            telemetry.update();
            drive.StrafeLeftDistance(1,12);
        }
    }

}